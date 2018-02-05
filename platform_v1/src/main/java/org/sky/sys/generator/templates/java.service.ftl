package ${basePackage}.service;
import org.apache.log4j.Logger;
import java.sql.Timestamp;
import java.util.List;
import org.sky.sys.client.SysCommonMapper;
import ${basePackage}.client.${className}Mapper;
import org.sky.sys.exception.ServiceException;
import ${basePackage}.model.${className};
import ${basePackage}.model.${className}Example;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
@Service
public class ${className}Service {
	private final Logger logger=Logger.getLogger(${className}Service.class);
	@Autowired
	private ${className}Mapper ${className?lower_case}mapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData get${className}ByPage(${className}Example ep){
		long totalCount = ${className?lower_case}mapper.countByExample(ep);
		List list = ${className?lower_case}mapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void save${className}(List<${className}> addlist,
			List<${className}> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(${className} add:addlist){
					${className?lower_case}mapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(${className} update:updatelist){
					${className?lower_case}mapper.updateByPrimaryKeySelective(update);
				}
			}
		}catch(Exception e){
			logger.error("保存列表新增及修改执行失败",e);
			if(e.getMessage().contains("的值太大")){
				throw new ServiceException("输入的字段值过长！");
			}
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*保存添加单个对象
	**/
	@Transactional
	public void saveAdd${className}(${className} add) throws ServiceException{
		try{
			${className?lower_case}mapper.insertSelective(add);
		}catch(Exception e){
			logger.error("保存添加单个对象执行失败",e);
			if(e.getMessage().contains("违反唯一约束条件")){
				throw new ServiceException("违反唯一约束条件");
			}else{
				throw new ServiceException(e.getMessage());
			}
		}
	}
	/**
	*保存新增/编辑单个对象
	**/
	@Transactional
	public void saveAddEdit${className}(${className} edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				${className?lower_case}mapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				${className?lower_case}mapper.updateByPrimaryKeySelective(edit);
			}
		}catch(Exception e){
			logger.error("保存新增/编辑单个对象执行失败",e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*根据主键批量删除对象
	**/
	@Transactional
	public void del${className}ById(List<${className}> delList){
		for(${className} del:delList){
			${className?lower_case}mapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public ${className} get${className}ById(String id){
		${className} bean = ${className?lower_case}mapper.selectByPrimaryKey(id);
		return bean;
	}
}