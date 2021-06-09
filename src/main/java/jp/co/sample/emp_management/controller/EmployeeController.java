package jp.co.sample.emp_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 * 名前による従業員曖昧検索
	 * 
	 * @param name 検索された名前
	 * @return 従業員一覧ページへのフォワード
	 */
	@RequestMapping("/searchByLikeName")
	public String searchByLikeName(String name, Integer pageNumber, Model model) {
		// ページ遷移用ボタンに設定するname属性
		model.addAttribute("name", name);

		// limitとoffsetをつけて従業員リストを追加
		int limit = 10;
		if (pageNumber == null) {
			pageNumber = 1;
		}
		List<Employee> employeeList = employeeService.showListByLimitAndPageNumber(name, limit, pageNumber);
		if (employeeList.size() == 0) {
			employeeList = employeeService.showList();
			model.addAttribute("message", "１件もありませんでした");
		}
		model.addAttribute("employeeList", employeeList);

		// ボタンを表示
		// 検索される名前に応じてページ数が変わるように実装
		List<Integer> pageNumbers = new ArrayList<>();
		int pages = employeeService.getNumbersEmployee(name) / limit;
		for (int i = 0; i <= pages; i++) {
			pageNumbers.add(i + 1);
		}
		model.addAttribute("pageNumbers", pageNumbers);
		return "employee/list";
	}
}
