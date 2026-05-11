# Splitmouse 🐭

**Splitmouse** es una aplicación nativa para Android diseñada para gestionar y dividir gastos grupales de forma sencilla y justa. Ideal para asados, viajes o salidas, la app calcula automáticamente los balances y optimiza las deudas entre los participantes.

## 📱 Capturas de Pantalla

<p align="center">
  <img width="1920" height="1440" alt="LinkedinPost1" src="https://github.com/user-attachments/assets/733ceef5-6159-4296-8944-d851027306fa" />
  <img width="1920" height="1440" alt="LinkedinPost2" src="https://github.com/user-attachments/assets/325a1ba9-0ab9-4bc3-9837-3fa49c2bd1dd" />

</p>

## 🚀 Características Principales

* **Gestión de Eventos:** Creación y organización de múltiples eventos con sus respectivos participantes.
* **Cálculo de Liquidación:** Algoritmo de resolución de deudas que minimiza la cantidad de transferencias necesarias.
* **Ruleta de la Suerte:** Un componente interactivo para decidir de forma aleatoria quién paga el próximo gasto.
* **Persistencia Local:** Almacenamiento seguro de datos en el dispositivo para uso sin conexión.

## 🛠️ Stack Tecnológico

* **Lenguaje:** Kotlin
* **UI:** Jetpack Compose + Material 3
* **Arquitectura:** MVVM (Model-View-ViewModel) + Clean Architecture
* **Inyección de Dependencias:** Koin
* **Base de Datos:** Room
* **Asincronía:** Kotlin Coroutines & StateFlow
* **Navegación:** Navigation Compose

## 📈 Optimización y Performance

Uno de los pilares del desarrollo fue la eficiencia técnica:
* **Diagnóstico:** Uso intensivo de **Android Studio Profiler** y **Layout Inspector** para garantizar una interfaz fluida y libre de recomposiciones innecesarias.
* **Estabilidad:** Implementación de modelos inmutables y optimización de estados para mejorar la velocidad de respuesta.
* **Optimización de recursos:** Aplicación de R8 para reducir el tamaño del APK y mejorar el rendimiento en tiempo de ejecución.

## 🚧 Roadmap

- [ ] Implementación de **Unit Testing** para blindar la lógica de cálculo de deudas.
      

---
Hecho por [Lautaro Ildarraz](https://www.linkedin.com/in/lautaro-ildarraz/) - Android Developer
