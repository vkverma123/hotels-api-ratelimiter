package com.hotels.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller.
 */
@Controller
public class HomeController {

  /**
   * Method to redirect to Swagger UI.
   */
  @RequestMapping("/")
  public String home() {
    return "redirect:swagger-ui.html";
  }
}
