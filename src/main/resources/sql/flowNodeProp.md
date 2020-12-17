selectNodePropList
===
```sql
    select fn.id              as flowNodeId,
           fnp.user_id        as userId,
           fnp.role_desc      as roleDesc,
           fnp.control_access as controlAccess,
           fnp.is_join        as isJoin,
           fnp.unit_id        as unitId,
           fnp.flow_role_id   as flowRoleId,
           fnp.assigner       as assigner,
           fnp.enable_cond    as enableCond,
           fnp.amount         as amount
    from flow f
             left join flow_node fn on f.id = fn.flow_id
             left join flow_node_prop fnp on fn.id = fnp.flow_node_id
    where flow_id = #{flowId}
    order by fn.sort_num 
```

selectAssignerList
===
```sql
    select distinct fnp.flow_role_id as id,
                    fr.role_name     as label
    from flow_node_prop fnp
         left join flow_role fr on fnp.flow_role_id = fr.id
         left join flow_node fn on fnp.flow_node_id = fn.id
         left join flow f on fn.flow_id = f.id
    where f.id = #{flowId}
```