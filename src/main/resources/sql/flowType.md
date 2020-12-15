selectIdByNodeId
===
```sql
    select ft.id
    from flow_type ft
         left join flow f on f.flow_type_id = ft.id
         left join flow_node fn on fn.flow_id = f.id
    where fn.id = #{flowNodeId}
```