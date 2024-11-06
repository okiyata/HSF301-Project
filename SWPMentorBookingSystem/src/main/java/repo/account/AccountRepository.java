package repo.account;

import java.util.List;

import pojo.Account;

public interface AccountRepository {
	Account save(Account account);

	Account update(Account account);

	void delete(Account account);

	void deleteById(Integer accountId);

	Account findById(Integer accountId);
	
	Account findByUserName(String username);

	List<Account> findAll();
}
