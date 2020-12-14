selectRoleUserIdByRoleName
===
```sql
    select user_id
    from flow_role_user fru
             left join flow_role fr on fru.flow_role_id = fr.id
    where fr.role_name = #{roleName}
```

selectFlowRoleByUserId
===
```sql
    select fru.flow_role_id
    from flow_role_user fru
    where fru.user_id = #{userId}
```