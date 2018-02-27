package dsl


import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer
import static org.codehaus.groovy.syntax.Types.*

class SimulationSensorDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private SimulationSensorBinding binding
	private SimulationSensorBasescript basescript	
	
	SimulationSensorDSL() {
		binding = new SimulationSensorBinding()
		binding.setSimulationSensorModel(new SimulationSensorModel(binding));
		configuration = getDSLConfiguration()
		configuration.setScriptBaseClass("dsl.SimulationSensorBasescript")
		shell = new GroovyShell(configuration)
		
		//binding.setVariable("high", SIGNAL.HIGH)
		//binding.setVariable("low", SIGNAL.LOW)
	}
	
	private static CompilerConfiguration getDSLConfiguration() {
		def secure = new SecureASTCustomizer()
		secure.with {
			//disallow closure creation
			closuresAllowed = true
			//disallow method definitions
			methodDefinitionAllowed = true
			//empty white list => forbid imports
			importsWhitelist = [
				'java.lang.*'
			]
			staticImportsWhitelist = []
			staticStarImportsWhitelist= ['java.lang.Math']
			//language tokens disallowed
//			tokensBlacklist= []
			//language tokens allowed
			tokensWhitelist= [
                         PLUS,
                         MINUS,
                         MULTIPLY,
                         DIVIDE,
                         MOD,
                         POWER,
                         PLUS_PLUS,
                         MINUS_MINUS,
                         COMPARE_EQUAL,
                         COMPARE_NOT_EQUAL,
                         COMPARE_LESS_THAN,
                         COMPARE_LESS_THAN_EQUAL,
                         COMPARE_GREATER_THAN,
                         COMPARE_GREATER_THAN_EQUAL,
                         LOGICAL_AND,
                         LOGICAL_AND_EQUAL,
                         LOGICAL_OR,
                         LOGICAL_OR_EQUAL,
                         LOGICAL_OPERATOR,
                         ].asImmutable()
			//types allowed to be used  (including primitive types)
			constantTypesClassesWhiteList= [
				int, Integer, Number, Integer.TYPE, String, Object, double, Closure
			].asImmutable()
			//classes who are allowed to be receivers of method calls
			receiversClassesWhiteList= [
				int, Number, Integer, String, Object, double, Closure
			].asImmutable()
		}

		final ImportCustomizer imports = new ImportCustomizer().addStaticStars('java.lang.Math')
		
		def configuration = new CompilerConfiguration()
		configuration.addCompilationCustomizers(imports, secure)
		
		return configuration
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()
	}
}
