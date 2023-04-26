package com.java.user.service;

import static com.java.view.AppUI.inputInteger;
import static com.java.view.AppUI.inputString;
import static com.java.view.AppUI.userManagementScreen;

import java.util.List;

import com.java.common.AppService;
import com.java.user.domain.User;
import com.java.user.repository.UserRepository;

public class UserService implements AppService {

	private final UserRepository userRepository = new UserRepository();
	/*
	 final이 붙은 클래스는 상속할 수 없다.
	 어떤 클래스를 상속하는데 그 안에 final 메서드가 있다면 오버라이딩으로 수정할 수 없다.
	 한 번 값을 할당하면 수정할 수 없다. 초기화는 한 번만 가능하다.
	  */
	@Override
	public void start() {
		while(true) {
			userManagementScreen();
			int selection = inputInteger();
			
			switch (selection) {
			case 1:
				join();
				break;
			case 2:
				showSearchResult();
				break;
			case 3:
				deleteUser();
				break;
			case 4:
				return; //메인 화면으로 돌아가기

			default:
				System.out.println("메뉴를 다시 입력하세요.");
			}
			System.out.println("\n====== 계속 진행하시려면 ENTER를 누르세요 ======");
			inputString();
		}
	}
	
	//회원 추가 비즈니스 로직
	private void join() {
		
		System.out.println("\n====== 회원 가입을 진행합니다. ======");
		System.out.print("# 회원명: ");
		String name = inputString();
		
		System.out.print("# 전화번호: ");
		String phone = inputString();
		
		User user = new User();
		user.setUserName(name);
		user.setPhoneNumber(phone);
			
		userRepository.addUser(user);
	}
	
	
	//회원 이름으로 검색 비즈니스 로직
	private List<User> searchUser() {
		System.out.println("\n### 조회할 회원의 이름을 입력하세요.");
		System.out.print(">>> ");
		String name = inputString();
		return userRepository.findByUserName(name);
		//입력받은 이름을 찾아서 list로 리턴
	}
	
	
	private int showSearchResult() {
		List<User> users = searchUser();
		
		if(users.size() > 0) {  //if(!users.isEmpty())
			System.out.println("\n========================= 회원 조회 결과 =========================");
			for(User user: users) {
				System.out.println(user);
			}
		} else {
			System.out.println("\n### 조회 결과가 없습니다.");
		}
		return users.size();
	}
	
	
	//회원 탈퇴 비즈니스 로직
	private void deleteUser() {
		
		//삭제할 회원의 이름을 입력받아서 삭제 대상 회원만 가지고온다.
		//List<User> users = searchUser();
				
		if(showSearchResult() > 0) {
			System.out.println("\n### 탈퇴할 회원의 번호를 입력하세요.");
			System.out.print(">>> ");
			int delUserNum = inputInteger();
			userRepository.deleteUser(delUserNum);
		} 
		
	}
	

}


