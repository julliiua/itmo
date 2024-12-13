      subroutine BISECT(a, b, eps, f, x0, k)
      interface
            function f(x)
                real(10) :: f
                real(10), intent(in) :: x
            end function f
      end interface
      
      real(10) :: x0, eps, a, b, an, bn, r, y ! Объявление переменных
      integer :: k

      k = 0          ! Счетчик итераций
      an = a         ! Начальная левая граница интервала
      bn = b         ! Начальная правая граница интервала
      r = f(a)       ! Значение функции в точке a

      do
            x0 = 0.5_16 * (an + bn) ! Вычисление середины интервала
            y = f(x0)

            if ((y == 0.0_10) .or. ((bn - an) <= (2.0_10 * eps))) exit ! Проверка

            k = k + 1 ! Обновление счетчика итераций и границ интервала
            if (sign(1.0_10, r) * sign(1.0_10, y) < 0.0_10) then
                bn = x0  ! Обновление правой границы интервала
            else
                an = x0  ! Обновление левой границы интервала
                r = y    ! Обновление значения функции в новой левой границе
            end if
      end do

      end subroutine BISECT

      program main
      interface
            function f(x)
                real(10) :: f
                real(10), intent(in) :: x
            end function f
      end interface

      real(10) :: a, b, eps, x ! Объявление вводимых переменных
      integer :: k

      ! Ввод данных
      print *, "Введите начальную левую границу интервала a:"
      read *, a
      print *, "Введите начальную правую границу интервала b:"
      read *, b
      print *, "Введите точность вычислений E:"
      read *, eps

      call BISECT(a, b, eps, f, x, k)

      print *, "Корень уравнения найден: "
      print "(A,F21.17)","x =", x ! Вывод результата с точностью 17 знаков после запятой
      print *, "Число итераций:", k

      end program main

      function f(x)
      implicit none
      real(10) :: f
      real(10), intent(in) :: x

      ! Вычисление значения функции
      f = exp(x) - 3.0_10 - cos(x)
      return
      end function f