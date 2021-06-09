package jp.co.sample.emp_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	/**
	 * 名前による従業員曖昧検索
	 * 
	 * @param name 検索された名前
	 * @return 名前曖昧検索による従業員リスト (名前がnullまたは空の場合全件返す)
	 */
	public List<Employee> showListByLikeName(String name) {
		if (name == null || name.equals("")) {
			return employeeRepository.findAll();
		}

		return employeeRepository.findByLikeName(name);
	}

	/**
	 * 全従業員数を取得.
	 * 
	 * @param name 曖昧検索用の名前
	 * @return 全従業員数
	 */
	public int getNumbersEmployee(String name) {
		if (name == null) {
			name = "";
		}
		return employeeRepository.getNumbersEmployee(name);
	}

	/**
	 * 指定した曖昧検索用の名前・数・ページ番号の従業員を取得.
	 * 
	 * @param name       曖昧検索用の名前
	 * @param limit      一回で取得する従業員数
	 * @param pageNumber 何ページ目の従業員リストか
	 * @return 従業員リスト
	 */
	public List<Employee> showListByLimitAndPageNumber(String name, int limit, int pageNumber) {
		int offset = limit * (pageNumber - 1);
		if (name == null) {
			name = "";
		}
		return employeeRepository.findByLikeNameLimitAndOffset(name, limit, offset);
	}
}
