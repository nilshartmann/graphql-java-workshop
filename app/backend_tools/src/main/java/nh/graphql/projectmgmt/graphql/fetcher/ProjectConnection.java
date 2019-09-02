package nh.graphql.projectmgmt.graphql.fetcher;

import java.util.LinkedList;
import java.util.List;

import nh.graphql.projectmgmt.domain.Project;

public class ProjectConnection {
  private PageResult pageResult;
  private List<Project> nodes;

  public static ProjectConnection fromList(List<Project> result, int page, int pageSize) {
    int from = page * pageSize;
    int to = from + pageSize;
    List<Project> nodes = from >= result.size() ? new LinkedList<Project>()
        : result.subList(from, to >= result.size() ? result.size() : to);

    boolean hasNextPage = to < result.size();
    boolean hasPreviousPage = from > 0;
    int totalPageCount = result.size() % pageSize == 0 ? result.size() / pageSize : ((result.size() / pageSize) + 1);
    PageResult pageResult = new PageResult(page, result.size(), totalPageCount < 1 ? 1 : totalPageCount, hasNextPage,
        hasPreviousPage);
    return new ProjectConnection(pageResult, nodes);
  }

  public ProjectConnection(PageResult pageResult, List<Project> nodes) {
    super();
    this.pageResult = pageResult;
    this.nodes = nodes;
  }

  public PageResult getPage() {
    return pageResult;
  }

  public List<Project> getNodes() {
    return nodes;
  }
}
