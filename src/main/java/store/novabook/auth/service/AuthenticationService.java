package store.novabook.auth.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.novabook.auth.entity.AuthenticationInfo;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

	private final RedisTemplate<String, Object> redisTemplate;

	public void saveAuth(AuthenticationInfo authenticationInfo) {
		if (Boolean.TRUE.equals(redisTemplate.hasKey(authenticationInfo.getUuid()))) {
			throw new IllegalArgumentException("Auth already exists for this uuid: " + authenticationInfo.getUuid());
		}
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(now, authenticationInfo.getExpirationTime());
		redisTemplate.opsForValue().set(authenticationInfo.getUuid(), authenticationInfo, duration);
	}

	public AuthenticationInfo getAuth(String uuid) {
		Object object = redisTemplate.opsForValue().get(uuid);
		if (object instanceof AuthenticationInfo) {
			return (AuthenticationInfo)object;
		} else {
			throw new IllegalArgumentException("No auth found with uuid: " + uuid);
		}
	}

	public boolean existsByUuid(String uuid) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(uuid));
	}

	public boolean deleteAuth(String uuid) {
		Object object = redisTemplate.opsForValue().get(uuid);
		if (object instanceof AuthenticationInfo) {
			redisTemplate.delete(uuid);
		} else {
			return false;
		}
		return true;
	}
}