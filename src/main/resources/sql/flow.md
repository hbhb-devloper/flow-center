selectPageByCond
===
```sql
    select 
    -- @pageTag(){
        f.id                 as flowId,
        f.flow_type_id       as flowTypeId,
        ft.flow_type_name    as flowTypeName,
        f.flow_name          as flowName,
        f.sort_num           as sortNum,
        f.remark             as remark,
        f.create_time        as createTime
    -- @}
    from flow f
        left join flow_type ft on ft.id = f.flow_type_id
    where f.delete_flag = 1
    -- @if(isNotEmpty(flowName)){
        and f.flow_name like concat('%', #{flowName}, '%')
    -- @}
    -- @if(isNotEmpty(flowTypeId)){
        and ft.id = #{flowTypeId}
    -- @}
    -- @pageIgnoreTag(){
        order by f.sort_num,f.create_time desc
    -- @}
```

selectInfoPageByCond
===
```sql
    select
    -- @pageTag(){
        fu.unit_id      as unitId,
        f.id            as flowId,
        f.flow_name     as flowName,
        fn.id           as flowNodeId,
        fn.node_name    as flowNodeName,
        fnp.role_desc   as roleDescription,
        fnp.user_id     as userId,
        fnp.unit_id     as roleRangeId,
        fr.role_name    as roleLid,
        fnp.enable_cond as enableCond
    -- @}
    from flow f
       left join flow_unit fu on f.id = fu.flow_id
       left join flow_node fn on f.id = fn.flow_id
       left join flow_node_prop fnp on fn.id = fnp.flow_node_id
       left join flow_role fr on fr.id = fnp.assigner
    where f.delete_flag = 1
    -- @if(isNotEmpty(unitId)){
        and fu.unit_id = #{unitId}
    -- @}
    -- @if(isNotEmpty(flowId)){
        and f.id = #{flowId}
    -- @}
    -- @pageIgnoreTag(){
        order by fn.id
    -- @}
```

selectNameByNodeId
===
```sql
    select f.flow_name
    from flow f
    left join flow_node fn on fn.flow_id = f.id
    where fn.id = #{nodeId}
```