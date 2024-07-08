package store.novabook.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record GetDormantMembersRequest(
	@NotBlank
	Long membersId) {
}