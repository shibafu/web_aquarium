package com.example.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.JdbcChusuiDao;
import com.example.entity.ChusuiUserMaster;
import com.example.repository.ChusuiUserMasterRepository;

@Service
public class ChuUserDetailService implements UserDetailsService {

	@Autowired
	ChusuiUserMasterRepository cumRepository;

	@Autowired
	JdbcChusuiDao jcDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		ChusuiUserMaster lo_cuMaster = Optional.ofNullable(jcDao.findByUseemail(username))
					.orElseThrow(() -> new  UsernameNotFoundException("user not be found"));

		return new ChuUserDetails(lo_cuMaster, getAuthority(lo_cuMaster));
	}

	/**
	 *
	 * @return
	 */
	private Collection<GrantedAuthority> getAuthority(ChusuiUserMaster x_cuMaster){
		if(x_cuMaster.getAuthority().equals("ROLE_ADMIN")){
			return AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
		} else {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}
	}
}