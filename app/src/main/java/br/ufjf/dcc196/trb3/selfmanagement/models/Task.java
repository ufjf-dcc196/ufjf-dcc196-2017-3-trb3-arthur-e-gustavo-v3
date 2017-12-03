package br.ufjf.dcc196.trb3.selfmanagement.models;

import java.util.List;

/**
 * Created by arthurlorenzi on 03/12/17.
 */

public class Task {

    private Integer id;
    private String title;
    private String description;
    private Integer difficulty;
    private TaskState state;
    private List<Tag> tags;

    private enum TaskState {
        DOING(3), TODO(2), WAIT(1), DONE(0);

        private final int priority;

        TaskState(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        boolean hasHigherPriorityThan(TaskState other) {
            return this.priority > other.getPriority();
        }
    }

    public Task() {
    }

    public Task(Integer id, String title, String description, Integer difficulty, TaskState state, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.state = state;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
