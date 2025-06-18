<h1 align="center">GlamVibe Client</h1>

<p align="center">
Приложение для клиентов салона красоты
</p>

</br>

<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/f043f15f-6a6c-42b9-a17d-f9890acae6a7"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/93fe7ec6-8216-4f12-ac41-43ccc3bb2d1e"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/2b34922f-5abf-4657-9839-b39961e03a8d"/>

## Функции приложения
- регистрация и авторизация в личном кабинете
- просмотр информации об услугах, акциях и мастерах салона красоты
- запись в салон с выбором услуги, мастера, даты и времени посещения
- управление записью через личный кабинет – перенос на другую дату или время, отмена
- добавление интересующих услуг в «Избранное» для последующей записи
- просмотр персональных рекомендаций услуг на основе информации о клиенте

### Регистрация
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/34ad84bc-7c8b-4bbf-9718-d4468bfe1450"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/ee52f894-afe1-4568-8ab7-c9f0d7cc5a27"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/c5c97578-bfc9-4daf-b967-2fafaeb8a103"/>

### Просмотр каталогов
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/ff8aab19-9376-4d10-9e42-83ba69363574"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/d3558257-3fca-418d-a04a-8433bb0b7707"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/f425bca2-019d-4a2e-8488-6fb2062054d5"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/ea74edfd-065b-4c99-8952-157d3f19f46e"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/61baeffd-ac17-442a-93b2-7edccff8262c"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/0e261dbc-e8ca-471f-8a48-ce61bc656b6b"/>

### Профиль клиента
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/19922e00-6a66-4d97-aacc-f3179e4eca0f"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/7116c95f-99f6-4bb0-a7d4-c9a7c0bd67c6"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/fda04919-4d24-43b3-9ecb-55ba4b40ed13"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/cb202096-4722-482f-8f27-4740beb9e2de"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/84ab313e-0e88-4f29-aead-5ce6e18ea8d0"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/33135ee3-4f0c-46c8-ba26-5f996f6c354b"/>

### Избранное
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/73bfc427-4df4-4551-9790-5a4fd9c8f6df"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/3e94ca32-76a1-4b12-b223-9eec783e6dee"/>

### Запись в салон
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/4d1b0c8b-1979-4ef5-bd94-ab8910654ca7"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/75023de1-64a4-481b-9ebf-615c00b89902"/>
<img alt="screenshot" width="30%" src="https://github.com/user-attachments/assets/879c7bf0-1cd8-4e36-855d-fa587983fb11"/>

## Особенности архитектуры
- MVVM
- Single Activity

## Используемые технологии
- Навигация между экранами с помощью Android Navigation
- RecyclerView для списков (услуги, мастера, акции, "Избранное" и др.)
- Glide для загрузки и обработки изображений
- Okhttp3 и Retrofit2 для работы с сетью
- Coroutines для асинхронных операций
- Koin для внедрения зависимостей
