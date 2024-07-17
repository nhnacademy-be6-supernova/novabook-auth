package store.novabook.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import store.novabook.auth.response.ApiResponse;
import store.novabook.auth.dto.CustomUserDetails;
import store.novabook.auth.dto.response.FindMemberLoginResponse;
import store.novabook.auth.dto.request.FindMembersRequest;
import store.novabook.auth.entity.AuthenticationMembers;

@Service
public class CustomMembersDetailsService implements UserDetailsService {

	private final CustomMembersDetailsClient customMembersDetailsClient;

	public CustomMembersDetailsService(CustomMembersDetailsClient customMembersDetailsClient) {
		this.customMembersDetailsClient = customMembersDetailsClient;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		FindMembersRequest findMembersRequest = new FindMembersRequest(username);

		ApiResponse<FindMemberLoginResponse> findMembersLoginResponseResponse = customMembersDetailsClient.find(
			findMembersRequest);

		AuthenticationMembers authenticationMembers = AuthenticationMembers.of(
			findMembersLoginResponseResponse.getBody().membersId(),
			findMembersLoginResponseResponse.getBody().loginId(),
			findMembersLoginResponseResponse.getBody().loginPassword(),
			findMembersLoginResponseResponse.getBody().role()
		);
		return new CustomUserDetails(authenticationMembers);
	}
}