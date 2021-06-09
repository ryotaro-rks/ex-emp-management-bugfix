package jp.co.sample.emp_management.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static String ALL_COLUMN_NAME = "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count";

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT " + ALL_COLUMN_NAME + " FROM employees order by hire_date";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception org.springframework.dao.DataAccessException 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT " + ALL_COLUMN_NAME + " FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}

	/**
	 * 名前による従業員の曖昧検索.
	 * 
	 * @param name 検索された名前
	 * @return 検索された名前の曖昧検索に一致した従業員のリスト
	 */
	public List<Employee> findByLikeName(String name) {
		String sql = "SELECT " + ALL_COLUMN_NAME + " FROM employees where name like :name order by hire_date";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		return template.query(sql, param, EMPLOYEE_ROW_MAPPER);
	}

	/**
	 * 全従業員数の取得.
	 * 
	 * @param name 曖昧検索用の名前
	 * @return 全従業員数
	 */
	public int getNumbersEmployee(String name) {
		return findByLikeName(name).size();
	}

	/**
	 * 従業員を範囲指定して取得.
	 * 
	 * @param name   曖昧検索用の名前
	 * @param limit  取得従業員数
	 * @param offset 取得開始行数
	 * @return 従業員リスト
	 */
	public List<Employee> findByLikeNameLimitAndOffset(String name, int limit, int offset) {
		String sql = "select " + ALL_COLUMN_NAME
				+ " from employees where name like :name order by hire_date limit :limit offset :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
				.addValue("limit", limit).addValue("offset", offset);
		return template.query(sql, param, EMPLOYEE_ROW_MAPPER);
	}
}
