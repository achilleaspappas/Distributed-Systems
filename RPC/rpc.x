struct dataOne {
    int n;
    int x<>;
    int y<>;
};

struct dataTwo {
    int n;
    double r;
    int x<>;
    int y<>;
};

struct resultVector {
    double result<>;
};

program INNERPRODUCTPROG {
    version KEKW {
        int innerProduct(dataOne)=1;
    }=1;
}=0x69696901;

program AVERAGEPROG {
    version KEKW {
        resultVector average(dataOne)=1;
    }=1;
}=0x69696902;

program MULTIPLICATIONPROG {
    version KEKW {
        resultVector multiplication(dataTwo)=1;
    }=1;
}=0x69696903;