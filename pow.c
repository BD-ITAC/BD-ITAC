#include <stdio.h>

main()
{
	int x, z;

	printf("Enter two numbers: ");
	scanf("%d %d", &x, &z);

	printf("\nResult: %.2f\n\n", por(x, z));
}
