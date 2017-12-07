package br.ufjf.dcc196.trb3.selfmanagement.models;

import java.util.List;

/**
 * Created by arthurlorenzi on 03/12/17.
 */

public class Task {

    private long id;
    private String title;
    private String description;
    private Integer difficulty;
    private TaskState state;
    private List<Tag> tags;

    public enum TaskState {
        DOING(3, "Em execução"), TODO(2, "A fazer"), WAIT(1, "Bloqueada"), DONE(0, "Concluída");

        private final int priority;
        private String displayName;

        TaskState(int priority, String displayName) {
            this.priority = priority;
            this.displayName = displayName;
        }

        TaskState(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        boolean hasHigherPriorityThan(TaskState other) {
            return this.priority > other.getPriority();
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public Task() {
    }

    public Task(String title, String description, Integer difficulty, TaskState state, List<Tag> tags) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.state = state;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
