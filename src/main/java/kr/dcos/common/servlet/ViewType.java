package kr.dcos.common.servlet;

/**
 * CmsMvc에서 해석할 수 있는 Resolver Type들
 * example: ForwardInfo fw = new ForwardInfo(ResolverType.HTML);
 * @author Administrator
 *
 */
public enum ViewType {
	HTML,JSON,DOWNLOAD,IMAGE,ERROR
}
