package io.dsub.sweatboys.opendiscogs.api.label.dto;

public record LabelReferenceDTO(Long id, String name) {
    public String getResourceURL() {
        return "https://api.opendiscogs.com/labels/" + id;
    }
}
