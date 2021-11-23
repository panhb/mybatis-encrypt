# mybatis-encrypt
mybatis数据加解密附带jackson脱敏

mybatis加解密   
1.TypeHandler方案     
(1)代码无侵入    
(2)只需关注mapper的xml文件   
(3)不支持resultType    

2.Interceptor    
(1)支持各种形式的出入参    
(2)注解上可以扩展更多的可能性      
(3)改动点增多      

mybatis可以实现加解密和脱敏，需要自己扩展      
防止业务逻辑要消费明文数据，附带jackson脱敏方案  


