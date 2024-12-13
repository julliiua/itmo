      subroutine BISECT(a, b, eps, f, x0, k)
      interface
            function f(x)
                real(10) :: f
                real(10), intent(in) :: x
            end function f
      end interface
      
      real(10) :: x0, eps, a, b, an, bn, r, y ! ���������� ����������
      integer :: k

      k = 0          ! ������� ��������
      an = a         ! ��������� ����� ������� ���������
      bn = b         ! ��������� ������ ������� ���������
      r = f(a)       ! �������� ������� � ����� a

      do
            x0 = 0.5_16 * (an + bn) ! ���������� �������� ���������
            y = f(x0)

            if ((y == 0.0_10) .or. ((bn - an) <= (2.0_10 * eps))) exit ! ��������

            k = k + 1 ! ���������� �������� �������� � ������ ���������
            if (sign(1.0_10, r) * sign(1.0_10, y) < 0.0_10) then
                bn = x0  ! ���������� ������ ������� ���������
            else
                an = x0  ! ���������� ����� ������� ���������
                r = y    ! ���������� �������� ������� � ����� ����� �������
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

      real(10) :: a, b, eps, x ! ���������� �������� ����������
      integer :: k

      ! ���� ������
      print *, "������� ��������� ����� ������� ��������� a:"
      read *, a
      print *, "������� ��������� ������ ������� ��������� b:"
      read *, b
      print *, "������� �������� ���������� E:"
      read *, eps

      call BISECT(a, b, eps, f, x, k)

      print *, "������ ��������� ������: "
      print "(A,F21.17)","x =", x ! ����� ���������� � ��������� 17 ������ ����� �������
      print *, "����� ��������:", k

      end program main

      function f(x)
      implicit none
      real(10) :: f
      real(10), intent(in) :: x

      ! ���������� �������� �������
      f = exp(x) - 3.0_10 - cos(x)
      return
      end function f