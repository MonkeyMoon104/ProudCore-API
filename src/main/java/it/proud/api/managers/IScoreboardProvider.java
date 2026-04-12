package it.proud.api.managers;

import java.util.Map;

/**
 * Contract for external modules that want to contribute scoreboard templates
 * to the ProudCore scoreboard system.
 */
public interface IScoreboardProvider {

    /**
     * Immutable scoreboard template composed of a title and a list of lines.
     *
     * @param title the sidebar title; must not be {@code null}
     * @param lines the ordered list of sidebar lines; must not be {@code null}
     */
    record ScoreboardTemplate(String title, java.util.List<String> lines) {
        public ScoreboardTemplate {
            java.util.Objects.requireNonNull(title, "title must not be null");
            java.util.Objects.requireNonNull(lines, "lines must not be null");
            lines = java.util.List.copyOf(lines);
        }
    }

    /**
     * Required per-platform template set for a scoreboard.
     *
     * <p>Both {@code javaTemplate} and {@code bedrockTemplate} are mandatory.</p>
     *
     * @param javaTemplate template used for Java players; must not be {@code null}
     * @param bedrockTemplate template used for Bedrock players; must not be {@code null}
     */
    record ScoreboardTemplateSet(ScoreboardTemplate javaTemplate,
                                 ScoreboardTemplate bedrockTemplate) {
        public ScoreboardTemplateSet {
            java.util.Objects.requireNonNull(javaTemplate, "javaTemplate must not be null");
            java.util.Objects.requireNonNull(bedrockTemplate, "bedrockTemplate must not be null");
        }

        public ScoreboardTemplate select(boolean bedrock) {
            return bedrock ? bedrockTemplate : javaTemplate;
        }
    }

    /**
     * Returns the unique, lowercase namespace identifier for this provider.
     *
     * @return provider namespace
     */
    String getProviderId();

    /**
     * Returns all scoreboard templates contributed by this provider.
     *
     * <p>Each template must provide both Java and Bedrock sections through
     * {@link ScoreboardTemplateSet}. Providers not respecting this requirement
     * are rejected by the registry with a concise error message.</p>
     *
     * @return non-null map of {@code templateName -> ScoreboardTemplateSet}
     */
    Map<String, ScoreboardTemplateSet> getTemplates();
}
