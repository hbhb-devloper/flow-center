batchInsert
===
```sql
    insert into flow_line
    (
        id,
        flow_id,
        from_node_id,
        to_node_id,
        label
    )
    values 
    -- @for(item in list){
    (
        #{item.id},
        #{item.flowId},
        #{item.from},
        #{item.to},
        #{item.label}
    )
    #{text(itemLP.last?"":"," )}
    -- @}
```