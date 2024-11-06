package repo.account;

import java.util.List;

import dao.AccountDAO;
import pojo.Account;

public class AccountRepositoryImpl implements AccountRepository {

	private AccountDAO dao;

	public AccountRepositoryImpl() {
		dao = new AccountDAO();
	}

	@Override
	public Account save(Account account) {
		dao.save(account);
		return dao.findById(account.getAccountID());
	}

	@Override
	public Account update(Account account) {
		dao.update(account);
		return dao.findById(account.getAccountID());
	}

	@Override
	public void delete(Account account) {
		dao.delete(account);
	}

	@Override
	public void deleteById(Integer accountId) {
		delete(dao.findById(accountId));
	}

	@Override
	public Account findById(Integer accountId) {
		return dao.findById(accountId);
	}

	@Override
	public List<Account> findAll() {
		return dao.findAll();
	}

	@Override
	public Account findByUserName(String username) {
		return dao.findByUserName(username);
	}

}
