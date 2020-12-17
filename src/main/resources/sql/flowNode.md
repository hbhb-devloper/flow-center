selectPageByCond
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
        u.nick_name     as userName,
        fnp.unit_id     as roleRangeId,
        fr.role_name    as roleLid,
        fnp.enable_cond as enableCond
    -- @}
    from flow f
       left join flow_unit fu on f.id = fu.flow_id
       left join flow_node fn on f.id = fn.flow_id
       left join flow_node_prop fnp on fn.id = fnp.flow_node_id
       left join flow_role fr on fr.id = fnp.assigner
       left join sys_user u on u.id = fnp.user_id
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

insertBatch
===
```sql
    insert into flow_node (id,
                           flow_id,
                           node_type,
                           node_name,
                           p_left,
                           p_top,
                           ico,
                           state,
                           sort_num)
    values
    -- @for(item in list){
        (#{item.id},
         #{item.flowId},
         #{item.nodeType},
         #{item.nodeName},
         #{item.pLeft},
         #{item.pTop},
         #{item.ico},
         #{item.state},
         #{item.sortNum}
        )
    -- @}
```