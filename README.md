# rest-service-calc
rest service calculator

1. Запускаем приложение и переходим в Postman
2. В бд есть два пользователя admin/admin и user/user
3. Вводим в окно authorization любого пользователя
4. Выбираем метод GET и прописываем путь http://localhost:8080/(имя пользователя) и в ответе получаем json в виде:

{

    "username": "admin",
    
    "expressions": []
   
}

5. Меняем на метод POST и прописываем путь http://localhost:8080/add
6. В теле запросы пишем любое выражение, например:


{
    
    "expression": "(2+3)/6",
   
    "precision": "5"
    
}

и получаем ответ в виде:

"ExpressionDTO : expression = '(2+3)/6', precision = '5', time = 23:58:56.665178600, date = 2021-03-11 Ответ = 0.83333"

Вычисленные выражения кешируются в коллекцию, и по достижению размера = 10 испольуется стратегия FIFO 

7. Снова ставим метод GET, прописываем путь http://localhost:8080/(имя пользователя) и видим результат в виде:

{

    "username": "admin",
    
    "expressions": [
    
        {
        
            "expression": "(2+3)/6",
            
            "precision": "5",
            
            "time": "00:00:57",
            
            "date": "2021-03-12"
            
        }
        
    ]
    
}
