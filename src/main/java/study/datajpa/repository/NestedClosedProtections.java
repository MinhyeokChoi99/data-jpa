package study.datajpa.repository;

public interface NestedClosedProtections {
    String getUsername();
    TeamInfo getTeam();

    interface  TeamInfo {
        String getName();
    }
}
