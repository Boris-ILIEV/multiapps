package org.cloudfoundry.multiapps.mta.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.cloudfoundry.multiapps.common.util.yaml.YamlAdapter;
import org.cloudfoundry.multiapps.common.util.yaml.YamlElement;
import org.cloudfoundry.multiapps.mta.parsers.v3.ModuleParser;
import org.cloudfoundry.multiapps.mta.util.MetadataConverter;

public class Module extends VersionedEntity
    implements VisitableElement, NamedParametersContainer, PropertiesWithMetadataContainer, ParametersWithMetadataContainer {

    @YamlElement(ModuleParser.NAME)
    private String name;
    @YamlElement(ModuleParser.TYPE)
    private String type;
    @YamlElement(ModuleParser.PATH)
    private String path;
    @YamlElement(ModuleParser.DESCRIPTION)
    private String description;
    @YamlElement(ModuleParser.PROPERTIES)
    private Map<String, Object> properties = Collections.emptyMap();
    @YamlElement(ModuleParser.PARAMETERS)
    private Map<String, Object> parameters = Collections.emptyMap();
    @YamlElement(ModuleParser.REQUIRES)
    private List<RequiredDependency> requiredDependencies = Collections.emptyList();
    @YamlElement(ModuleParser.PROVIDES)
    private List<ProvidedDependency> providedDependencies = Collections.emptyList();
    @YamlElement(ModuleParser.PROPERTIES_METADATA)
    @YamlAdapter(MetadataConverter.class)
    private Metadata propertiesMetadata = Metadata.DEFAULT_METADATA;
    @YamlElement(ModuleParser.PARAMETERS_METADATA)
    @YamlAdapter(MetadataConverter.class)
    private Metadata parametersMetadata = Metadata.DEFAULT_METADATA;
    @YamlElement(ModuleParser.DEPLOYED_AFTER)
    private List<String> deployedAfter;
    @YamlElement(ModuleParser.HOOKS)
    private List<Hook> hooks = Collections.emptyList();

    // Required by Jackson.
    protected Module() {
        super(0);
    }

    protected Module(int majorSchemaVersion) {
        super(majorSchemaVersion);
    }

    public static Module copyOf(Module original) {
        Module copy = new Module(original.majorSchemaVersion);
        copy.name = original.name;
        copy.type = original.type;
        copy.path = original.path;
        copy.description = original.description;
        copy.properties = new TreeMap<>(original.properties);
        copy.parameters = new TreeMap<>(original.parameters);
        copy.requiredDependencies = copyRequiredDependencies(original.requiredDependencies);
        copy.providedDependencies = copyProvidedDependencies(original.providedDependencies);
        copy.propertiesMetadata = original.propertiesMetadata;
        copy.parametersMetadata = original.parametersMetadata;
        copy.deployedAfter = original.deployedAfter == null ? null : new ArrayList<>(original.deployedAfter);
        copy.hooks = copyHooks(original.hooks);
        return copy;
    }

    private static List<Hook> copyHooks(List<Hook> originalHooks) {
        return originalHooks.stream()
                            .map(Hook::copyOf)
                            .collect(Collectors.toList());
    }

    private static List<RequiredDependency> copyRequiredDependencies(List<RequiredDependency> originals) {
        return originals.stream()
                        .map(RequiredDependency::copyOf)
                        .collect(Collectors.toList());
    }

    private static List<ProvidedDependency> copyProvidedDependencies(List<ProvidedDependency> originals) {
        return originals.stream()
                        .map(ProvidedDependency::copyOf)
                        .collect(Collectors.toList());
    }

    public static Module createV2() {
        return new Module(2);
    }

    public static Module createV3() {
        return new Module(3);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    public List<RequiredDependency> getRequiredDependencies() {
        return requiredDependencies;
    }

    public List<ProvidedDependency> getProvidedDependencies() {
        return providedDependencies;
    }

    @Override
    public Metadata getPropertiesMetadata() {
        supportedSince(3);
        return propertiesMetadata;
    }

    @Override
    public Metadata getParametersMetadata() {
        supportedSince(3);
        return parametersMetadata;
    }

    public List<String> getDeployedAfter() {
        supportedSince(3);
        return deployedAfter;
    }

    public List<Hook> getHooks() {
        supportedSince(3);
        return hooks;
    }

    public Module setName(String name) {
        this.name = ObjectUtils.defaultIfNull(name, this.name);
        return this;
    }

    public Module setType(String type) {
        this.type = ObjectUtils.defaultIfNull(type, this.type);
        return this;
    }

    public Module setPath(String path) {
        this.path = ObjectUtils.defaultIfNull(path, this.path);
        return this;
    }

    public Module setDescription(String description) {
        this.description = ObjectUtils.defaultIfNull(description, this.description);
        return this;
    }

    @Override
    public Module setProperties(Map<String, Object> properties) {
        this.properties = ObjectUtils.defaultIfNull(properties, this.properties);
        return this;
    }

    @Override
    public Module setParameters(Map<String, Object> parameters) {
        this.parameters = ObjectUtils.defaultIfNull(parameters, this.parameters);
        return this;
    }

    public Module setRequiredDependencies(List<RequiredDependency> requiredDependencies) {
        this.requiredDependencies = ObjectUtils.defaultIfNull(requiredDependencies, this.requiredDependencies);
        return this;
    }

    public Module setProvidedDependencies(List<ProvidedDependency> providedDependencies) {
        this.providedDependencies = ObjectUtils.defaultIfNull(providedDependencies, this.providedDependencies);
        return this;
    }

    @Override
    public Module setPropertiesMetadata(Metadata propertiesMetadata) {
        supportedSince(3);
        this.propertiesMetadata = ObjectUtils.defaultIfNull(propertiesMetadata, this.propertiesMetadata);
        return this;
    }

    @Override
    public Module setParametersMetadata(Metadata parametersMetadata) {
        supportedSince(3);
        this.parametersMetadata = ObjectUtils.defaultIfNull(parametersMetadata, this.parametersMetadata);
        return this;
    }

    public Module setDeployedAfter(List<String> deployedAfter) {
        supportedSince(3);
        this.deployedAfter = ObjectUtils.defaultIfNull(deployedAfter, this.deployedAfter);
        return this;
    }

    public Module setHooks(List<Hook> hooks) {
        supportedSince(3);
        this.hooks = ObjectUtils.defaultIfNull(hooks, this.hooks);
        return this;
    }

    @Override
    public void accept(ElementContext context, Visitor visitor) {
        visitor.visit(context, this);
        for (ProvidedDependency providedDependency : providedDependencies) {
            providedDependency.accept(new ElementContext(providedDependency, context), visitor);
        }
        for (RequiredDependency requiredDependency : requiredDependencies) {
            requiredDependency.accept(new ElementContext(requiredDependency, context), visitor);
        }
        for (Hook hook : hooks) {
            hook.accept(new ElementContext(hook, context), visitor);
        }
    }

}
