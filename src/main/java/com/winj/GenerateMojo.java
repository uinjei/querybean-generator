package com.winj;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import io.ebean.typequery.generator.Generator;
import io.ebean.typequery.generator.GeneratorConfig;

/**
 * @author Edwin Jay Javier
 *
 */
@Mojo(name="generate", defaultPhase=LifecyclePhase.COMPILE )
public class GenerateMojo extends AbstractMojo {
	
	@Parameter(property="entityBeanPackage", required=true )
    private String entityBeanPackage;
	
	@Parameter(property="destPackage", required=true )
    private String destPackage;
	
    public void execute() throws MojoExecutionException {
    	var config = new GeneratorConfig();

		config.setEntityBeanPackage(entityBeanPackage);
		config.setDestPackage(destPackage);

		config.setOverwriteExistingFinders(true);
		var generator = new Generator(config);
		try {
			generator.generateQueryBeans();
			generator.generateFinders();
			generator.modifyEntityBeansAddFinderField();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
