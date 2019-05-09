package com.in28minutes.todo;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class TodoController {

	@Autowired
	TodoService service;

	@RequestMapping(value = "list-todos", method = RequestMethod.GET)
	public String listTodos(ModelMap model) {
		String name = String.valueOf(model.get("name"));
		model.addAttribute("todos", service.retrieveTodos(name));
		return "list-todos";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String showTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo(0, String.valueOf(model.get("name")), "", new Date(), false));
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		} else {
			service.addTodo(String.valueOf(model.get("name")), todo.getDesc(), new Date(), false);

			model.clear();
			return "redirect:list-todos";
		}
	}

	@RequestMapping(value = "edit-todo", method = RequestMethod.GET)
	public String editTodo(ModelMap model, @RequestParam int id) {
		Todo selectedTodo = service.retrieveTodo(id);
		model.put("todo", selectedTodo);
		return "todo";
	}

	@RequestMapping(value = "delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id, ModelMap model) {

		service.deleteTodo(id);

		model.clear();
		return "redirect:list-todos";
	}
};