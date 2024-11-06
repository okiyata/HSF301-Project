package service.account;

import java.util.List;

import pojo.Account;

public interface AccountService {
	Account save(Account account);

	Account update(Account account);

	void delete(Account account);

	void deleteById(Integer accountId);

	Account findById(Integer accountId);

	List<Account> findAll();
	
	Account authenticate(String username, String password);
}
