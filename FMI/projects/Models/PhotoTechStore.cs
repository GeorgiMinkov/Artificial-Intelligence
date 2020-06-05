using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml.Serialization;

namespace PhotoStudio.Models
{
    [Serializable, XmlRoot("photo_tech_store")]
    public class PhotoTechStore
    {
        [XmlElement("photo_cameras")]
        public PhotoCameras photoCameras { set; get; }

        [XmlElement("photo_lenses")]
        public PhotoLenses photoLenses { set; get; }

        [XmlElement("photo_flashes")]
        public PhotoFlashes photoFlashes { set; get; }

        [XmlElement("photo_filters")]
        public PhotoFilters photoFilters { set; get; }

        [XmlElement("photo_tripods")]
        public PhotoTripods photoTripods { set; get; }
    }
}