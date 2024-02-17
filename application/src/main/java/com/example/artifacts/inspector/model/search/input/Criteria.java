package com.example.artifacts.inspector.model.search.input;

public class Criteria {

    private String value;
    private ComparisonType comparison = ComparisonType.EQUAL;

    public Criteria() {
    }

    public Criteria(String value, ComparisonType comparison) {
        this.value = value;
        this.comparison = comparison;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ComparisonType getComparison() {
        return comparison;
    }

    public void setComparison(ComparisonType comparison) {
        this.comparison = comparison;
    }
}
