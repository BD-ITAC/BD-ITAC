#include <stdio.h>

main()
{
	int x, y;

	printf("Enter a number: ");
	scanf("%d", &x);
	printf("Enter another number: ");
	scanf("%d", &y);

	printf("\nResult: %.2f\n\n", pow(x, y));
}
