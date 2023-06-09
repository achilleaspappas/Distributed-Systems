/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include <memory.h> /* for memset */
#include "rpc.h"

/* Default timeout can be changed using clnt_control() */
static struct timeval TIMEOUT = { 25, 0 };

int *
innerproduct_1(dataOne *argp, CLIENT *clnt)
{
	static int clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, innerProduct,
		(xdrproc_t) xdr_dataOne, (caddr_t) argp,
		(xdrproc_t) xdr_int, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

resultVector *
average_1(dataOne *argp, CLIENT *clnt)
{
	static resultVector clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, average,
		(xdrproc_t) xdr_dataOne, (caddr_t) argp,
		(xdrproc_t) xdr_resultVector, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

resultVector *
multiplication_1(dataTwo *argp, CLIENT *clnt)
{
	static resultVector clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, multiplication,
		(xdrproc_t) xdr_dataTwo, (caddr_t) argp,
		(xdrproc_t) xdr_resultVector, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}
