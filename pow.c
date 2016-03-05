#include <stdio.h>

main()
{
	int x, y;

	printf("Enter two numbers: ");
	scanf("%d %d", &x, &y);

	printf("\nResult: %.2f\n\n", por(x, y));
}
