package org.openbakery

import org.gradle.api.DefaultTask
import org.gradle.util.ConfigureUtil
import org.openbakery.simulators.SimulatorControl
import org.openbakery.tools.DestinationResolver
import org.openbakery.tools.Xcode
import org.openbakery.tools.XcodebuildParameters

/**
 * User: rene
 * Date: 15.07.13
 * Time: 11:57
 */
abstract class AbstractXcodeBuildTask extends DefaultTask {

	CommandRunner commandRunner
	Xcode xcode
	XcodebuildParameters parameters = new XcodebuildParameters()
	SimulatorControl simulatorControl
	DestinationResolver destinationResolver
	private List<Destination> destinationsCache


	AbstractXcodeBuildTask() {
		super()
		commandRunner = new CommandRunner()
		xcode = new Xcode(commandRunner, project.xcodebuild.xcodeVersion)
		this.simulatorControl = new SimulatorControl(project, this.commandRunner, xcode)
		this.destinationResolver = new DestinationResolver(this.simulatorControl)
	}


	void setTarget(String target) {
		parameters.target = target
	}

	void setScheme(String scheme) {
		parameters.scheme = scheme
	}

	void setSimulator(Boolean simulator) {
		parameters.simulator = simulator
	}

	void setType(Type type) {
		parameters.type = type
	}

	void setWorkspace(String workspace) {
		parameters.workspace = workspace
	}

	void setAdditionalParameters(String additionalParameters) {
		parameters.additionalParameters = additionalParameters
	}

	void setConfiguration(String configuration) {
		parameters.configuration = configuration
	}

	void setArch(List<String> arch) {
		parameters.arch = arch
	}

	void setConfiguredDestinations(Set<Destination> configuredDestination) {
		parameters.configuredDestinations = configuredDestination
	}

	void setDevices(Devices devices) {
		parameters.devices = devices
	}


	void destination(Closure closure) {
		parameters.destination(closure)
	}

	void setDestination(def destination) {
		parameters.setDestination(destination)
	}

	List<Destination> getDestinations() {
		if (destinationsCache == null) {
			destinationsCache = destinationResolver.getDestinations(parameters)
		}
		return destinationsCache
	}
}
