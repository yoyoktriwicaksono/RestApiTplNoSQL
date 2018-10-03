package org.wicaksono.rest.nosql.servlet;

import com.google.inject.Singleton;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Swagger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author cluttered.code@gmail.com
 */
@Singleton
public class SwaggerBootstrap extends HttpServlet {

  @Override
  public void init(final ServletConfig config) throws ServletException {
    Swagger swagger = new Swagger();
    new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
  }
}