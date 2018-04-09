from pyspark.sql import SparkSession
from pyspark import SparkContext
from pyspark.sql.types import DoubleType
import json
class PythonExecutorBasic:
    def __init__(self):
        self.spark = SparkSession.builder.getOrCreate()
        df = self.spark.read.csv("data/sample_loan_data.csv",header=True)
        df = df.select("loanId", "annualInc", "empLength")
        df.registerTempTable("test_table")

    def compute_feature(self, feature_json_str):
        """

        :param feature_json_str: A feature json of type
        {
            "name":
            "input":
            "val":
        }
        :return: None
        """
        feature_json = json.loads(feature_json_str)
        name = feature_json["name"]
        args = ",".join(feature_json["input"])
        func_def = "def {0}({1}):".format(name,args)
        func_def += "\n\t"+feature_json["val"]
        exec func_def
        self.spark.udf.register(name, locals()[name],DoubleType())
        new_df = self.spark.sql("select *, {0}({1}) as {0} from test_table".format(name, args))
        new_df.show()

    class Java:
        implements = ["py4j.examples.IPythonExecutorBasic"]



from py4j.java_gateway import JavaGateway, CallbackServerParameters
python_executor = PythonExecutorBasic()
gateway = JavaGateway(
    callback_server_parameters=CallbackServerParameters(port=25334),
    python_server_entry_point=python_executor)

