package com.akolchin.stmg.server;

import java.util.ArrayList;
import java.util.List;

import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.appengine.api.utils.SystemProperty;

public class ServerUtils {
	public static boolean isProduction() {
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
	}

	public static UserDto getUserDto(boolean isLoggedIn, AppUser appUser) {
		UserDto userDto = new UserDto();

		userDto.setAttributes(isLoggedIn, appUser.getEmail(), appUser.getName(), appUser.getCash(), appUser.getValue(),
				appUser.getReturnPct(), appUser.getRank());

		return userDto;
	}

	public static List<UserDto> getUserDtos(List<AppUser> appUsers) {
		List<UserDto> userDtos = new ArrayList<UserDto>(appUsers.size());
		for (AppUser appUser : appUsers) {
			userDtos.add(getUserDto(false, appUser));
		}
		return userDtos;

	}
}
