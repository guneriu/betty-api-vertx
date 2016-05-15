package com.guneriu.betty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by ugur on 14.05.2016.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Season {
    private Long id;

    private String caption;

    private String league;

    private Integer year;

    @JsonProperty(value = "currentMatchday")
    private Integer currentMatchDay;

    private Integer numberOfMatchdays;

    private Integer numberOfTeams;

    private Integer numberOfGames;

    private String lastUpdated;



    @JsonProperty(value = "_links")
    private Map<String, Map<String, String>> links;

}
