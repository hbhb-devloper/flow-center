selectPageByCond
===
```sql
    select fru.id       as id,
           u.unit_name  as unitName,
           fr.role_name as roleName,
           su.nick_name as nickName
    from flow_role fr
             left join flow_role_user fru on fru.flow_role_id = fr.id
             left join sys_user su on fru.user_id = su.id
             left join unit u on u.id = su.unit_id
    -- @where(){
        -- @if(isNotEmpty(cond.unitId)){
            and u.id = #{cond.unitId}
        -- @}
        -- @if(isNotEmpty(cond.flowRoleId)){
            and fr.id = #{cond.flowRoleId}
        -- @}
        -- @if(isNotEmpty(cond.nickName)){
            and su.nick_name like concat('%', #{cond.nickName}, '%')
        -- @}
    -- @}
```
