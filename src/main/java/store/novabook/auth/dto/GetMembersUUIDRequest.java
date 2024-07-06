package store.novabook.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record GetMembersUUIDRequest(
	@NotBlank
	String uuid
) {
}
