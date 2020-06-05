using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhotoStudio.Models
{
    public class Filter
    { 
        public string filter_name { set; get; }
        public string filter_image { set; get; }
        public string filter_type { set; get; }
        public int filter_description { set; get; }
        public string filter_manifacturer { set; get; }

        // float flash_price (attribiutes (all optional): string currency)
    }
}