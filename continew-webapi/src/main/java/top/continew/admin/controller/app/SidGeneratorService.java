package top.continew.admin.controller.app;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class SidGeneratorService {

    private static final String REDIS_KEY_SIDS_BITMAP = "app:sids:bitmap";
    private static final int MAX_RETRIES = 50;

    private static final int SID_MIN_VALUE = 10000;
    private static final int SID_MAX_VALUE = 99999; // (10000 + 90000 - 1)

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisScript<Long> checkAndSetSidScript = new DefaultRedisScript<>(
            """
                    local current_value = redis.call('GETBIT', KEYS[1], ARGV[1])
                    if current_value == 0 then
                      redis.call('SETBIT', KEYS[1], ARGV[1], 1)
                      return 1
                    else
                      return 0
                    end""", Long.class);

    public int generateUniqueSid() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < MAX_RETRIES; i++) {
            int candidateSid = random.nextInt(SID_MIN_VALUE, SID_MAX_VALUE + 1);
            long offset = (long) candidateSid - SID_MIN_VALUE;

            Long result = stringRedisTemplate.execute(
                    checkAndSetSidScript,
                    Collections.singletonList(REDIS_KEY_SIDS_BITMAP),
                    String.valueOf(offset)
            );

            if (result == 1L) {
                return candidateSid;
            }
        }
        // If we reach here, all retries failed.
        // This is more likely to happen if the SID space (90,000 SIDs) is nearly full.
        throw new RuntimeException("Failed to generate a unique SID after " + MAX_RETRIES +
                " retries. The SID space (10000-99999) might be exhausted or Redis unavailable.");
    }

    // Optional: A method to check how many SIDs are used (for monitoring)
    public long getUsedSidCount() {
        return stringRedisTemplate.execute(
                new DefaultRedisScript<>("return redis.call('BITCOUNT', KEYS[1])", Long.class),
                Collections.singletonList(REDIS_KEY_SIDS_BITMAP)
        );
    }

    public boolean isSidUsed(int sid) {
        if (sid < SID_MIN_VALUE || sid > SID_MAX_VALUE) {
            return false;
        }
        long offset = (long) sid - SID_MIN_VALUE;
        Boolean isSet = stringRedisTemplate.opsForValue().getBit(REDIS_KEY_SIDS_BITMAP, offset);
        return Boolean.TRUE.equals(isSet);
    }
}
