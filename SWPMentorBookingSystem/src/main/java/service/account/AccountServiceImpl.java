package service.account;

import java.util.List;

import pojo.Account;
import repo.account.AccountRepository;
import repo.account.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService{
	
	private AccountRepository repo;
	
	public AccountServiceImpl () {
		repo = new AccountRepositoryImpl();
	}
	
	@Override
	public Account save(Account account) {
		return repo.save(account);
	}

	@Override
	public Account update(Account account) {
		return repo.update(account);
	}

	@Override
	public void delete(Account account) {
		repo.delete(account);
	}
	
	@Override
	public void deleteById (Integer accountId) {
		repo.deleteById(accountId);
	}

	@Override
	public Account findById(Integer accountId) {
		return repo.findById(accountId);
	}

	@Override
	public List<Account> findAll() {
		return repo.findAll();
	}

	@Override
	public Account authenticate(String userName, String password) {
		Account cust = repo.findByUserName(userName);
		System.out.println(cust);
		if (cust != null && cust.getPassword().equals(password))
			return cust;
		return null;
	}
	
}
