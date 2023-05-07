/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "rpc.h"
#include <math.h>

int *
innerproduct_1_svc(dataOne *argp, struct svc_req *rqstp)
{
	static int  result;

	/* Start of custom code */
	int resultTemp = 0;

	for (int i = 0; i < argp->n; i++)
	{
		resultTemp += argp->x.x_val[i] * argp->y.y_val[i];
	}

	result = resultTemp;
	printf("Inner product calculated and sent to RPC Client\n");
	/* End of custom code */
	
	return &result;
}

resultVector *
average_1_svc(dataOne *argp, struct svc_req *rqstp)
{
	static resultVector  result;

	/* Start of custom code */
	double xSum = 0;
	double ySum = 0;

	result.result.result_len = 2;
	result.result.result_val = (double *)malloc(sizeof(double) * 2);

	for (int i = 0; i < argp->n; i++) {
		xSum += argp->x.x_val[i];
		ySum += argp->y.y_val[i];
	}

	result.result.result_val[0] = xSum / argp->n;
	result.result.result_val[1] = ySum / argp->n;
	printf("Average calculated and sent to RPC Client.\n");
	/* End of custom code */

	return &result;
}

resultVector *
multiplication_1_svc(dataTwo *argp, struct svc_req *rqstp)
{
	static resultVector  result;

	/* Start of custom code */
	result.result.result_len = argp->n;
	result.result.result_val = (double *)malloc(sizeof(double) * argp->n);

	for (int i = 0; i < argp->n; i++) {
		result.result.result_val[i] = argp->r * (argp->x.x_val[i] + argp->y.y_val[i]);
	}
	printf("r*(X+Y) calculated and sent to RPC Client.\n");
	/* End of custom code */

	return &result;
}
