#include <stdio.h>

main()
{
	int x;

	printf("Enter two numbers: ");
	scanf("%d", &x);

	printf("\nResult: %.2f\n\n", por(x, 2));
}
